
import java.util.Scanner;

/**
 * 无法从网页上获得源代码
 */

public class GetScore_jisuanke {
	
	static String PREFIX_UNICODE= "\\u";
	static char ascii2Char(String str) { 
		if (str.length() != 6) { 
			throw new IllegalArgumentException("Ascii string of a native character must be 6 character."); 
		} 
		if (!PREFIX_UNICODE.equals(str.substring(0, 2))) { 
			throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\"."); 
		} 
		String tmp = str.substring(2, 4); // 将十六进制转为十进制 
		int code = Integer.parseInt(tmp, 16) << 8; // 转为高位，后与地位相加 
		tmp = str.substring(4, 6); 
		code += Integer.parseInt(tmp, 16); // 与低8为相加 
		return (char) code; 
	} 
	
	static String ascii2Native(String str) { 
		StringBuilder sb = new StringBuilder(); 
		int begin = 0; 
		int index = str.indexOf(PREFIX_UNICODE); 
		while (index != -1) { 
			sb.append(str.substring(begin, index)); 
			sb.append(ascii2Char(str.substring(index, index + 6))); 
			begin = index + 6; index = str.indexOf(PREFIX_UNICODE, begin); 
		} 
		sb.append(str.substring(begin)); 
		return sb.toString(); 
	}

	/*
	 * unicode代码  来自 黑暗的笑 的CSDN 博客 ，全文地址请点击：https://blog.csdn.net/xia744510124/article/details/51322107?utm_source=copy 
	 */
	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		String str,s;
		int s1,s2,s3;
		String tag=new String("</script>");
		int x,y,sum_pro,i;
		
		while ((str=in.nextLine())!=null) {
			if (str.length()>=9 && str.substring(0,9).equals(tag)) {

				s="problem_naming";
				x=str.indexOf(s);
				x+=s.length()+3;
				y=str.indexOf("]",x);
				sum_pro=(y-x)/4;

				System.out.print("team\tschool\tcount\ttime\t");
				for (i=0;i<sum_pro;i++)
					System.out.print((char)(65+i)+"\t");
				System.out.println();
				
				y=str.indexOf("prev_page_url",y);
				
				while (true) {
					s="name";
					x=str.indexOf(s,y);
					if (x==-1)
						break;
					x+=s.length()+3;
					y=str.indexOf("\"",x);
					System.out.print(str.substring(x,y)+"\t");
					
					s="school";
					x=str.indexOf(s,y);
					x+=s.length()+3;
					y=str.indexOf("\"",x);
					System.out.print(ascii2Native(str.substring(x,y))+"\t");
					
					s="score";
					x=str.indexOf(s,y);
					x+=s.length()+2;
					y=str.indexOf(",",x);
					System.out.print(str.substring(x,y)+"\t");

					s="cost";
					x=str.indexOf(s,y);
					x+=s.length()+2;
					y=str.indexOf(",",x);
					System.out.print(str.substring(x,y)+"\t");
					
					// until not exists or ==cost  -1
					for (i=1;i<=sum_pro;i++) {
						//cost":120,"exact_cost":7144,"submit_count":4,"problem_score":1,"score":0
						s="cost\"";
						x=str.indexOf(s,y);
						x+=s.length()+1;	//2-1
						y=str.indexOf(",",x);
						s1=Integer.valueOf(str.substring(x,y));

						s="exact_cost";
						x=str.indexOf(s,y);
						x+=s.length()+2;
						y=str.indexOf(",",x);
						s2=Integer.valueOf(str.substring(x,y));
						
						s="submit_count";
						x=str.indexOf(s,y);
						x+=s.length()+2;
						y=str.indexOf(",",x);
						s3=Integer.valueOf(str.substring(x,y));
						
						if (s2!=0)
							System.out.print(s1);
						else
							System.out.print("——");
						System.out.print("("+s3+")\t");
					}
					System.out.println();
				}
			}
		}
	}
}
