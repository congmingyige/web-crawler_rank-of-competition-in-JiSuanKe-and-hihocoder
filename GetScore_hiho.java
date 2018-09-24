/**
 * get source code:
 * https://www.cnblogs.com/chaohu13/p/5337498.html
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetScore_hiho {
    public static void main(String args[]){    
        URL url;
        int responsecode;
        HttpURLConnection urlConnection;
        BufferedReader reader;
        String str,str1;
        String tag=new String("<tr class=\"std-acm\">");
        String website;
	//修改1 必须是"rank?page="形式
        website=new String("http://hihocoder.com/contest/acmicpc2018beijingonline/rank?page=1");
        int x,y,i;
	//修改2
        int page=13;
	int index=0;
        Boolean vis;
        
        vis=false;	//首栏只用存在一次
        for (index=1;index<=page;index++) {
	        try{
	            //生成一个URL对象，要获取源代码的网页地址为：http://www.sina.com.cn
	        	x=website.indexOf("=");
	        	website=website.substring(0,x+1)+String.valueOf(index);
	            url=new URL(website);
	            
	            //打开URL
	            urlConnection = (HttpURLConnection)url.openConnection();
	            //获取服务器响应代码
	            responsecode=urlConnection.getResponseCode();
	            if(responsecode==200){
	                //得到输入流，即获得了网页的内容
	                reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));//GBK
	                while((str=reader.readLine().trim())!=null){
	//                	System.out.println(str);	//test
	                	if (str.equals(tag)==true) {
	                		str=reader.readLine().trim();
                			x=str.indexOf(">");
                			y=str.indexOf("<",x);

	                		if (str.substring(x+1,y).equals("Rank")==true) {
	                			if (vis==false) {
	                				vis=true;
	                				System.out.print(str.substring(x+1,y).trim()+"\t");
		                			while (true) {
		                				str=reader.readLine().trim();
		                				if (str.equals("</tr>")==true)
		                					break;
		                    			x=str.indexOf(">");
		                    			y=str.indexOf("<",x);
		                				System.out.print(str.substring(x+1,y).trim()+"\t");
		                				
		                				if ((x=str.indexOf(">",y))!=str.length()-1) {
		                        			y=str.indexOf("<",x);
		                    				System.out.print(str.substring(x+1,y).trim()+"\t");
		                				}
		                			}
		                			System.out.println();
	                			}
//	                			System.exit(0);	//test
	                		}
	                		else {
			            		/*
			            		 * <td>1</td>
			            		 * <td>清华大学</td>
			            		 */
	            				System.out.print(str.substring(x+1,y).trim()+"\t");
		                		for (i=2;i<=2;i++) {	//1+1
				            		str=reader.readLine().trim();
			            			x=str.indexOf(">");
			            			y=str.indexOf("<",x);
			        				System.out.print(str.substring(x+1,y).trim()+"\t");	
		                		}
		
		                		//<td><a class="fn-ell" style="display: block;" href="/user/109506">team181814</a></td>
			            		str=reader.readLine().trim();
		            			x=str.indexOf(">",5);
		            			y=str.indexOf("<",x);
		        				System.out.print(str.substring(x+1,y).trim()+"\t");	
		
		        				/*
		        				 * <td class="solved">8</td>
					             * <td>15:20:09</td>
		        				 */
		                		for (i=1;i<=2;i++) {
				            		str=reader.readLine().trim();
			            			x=str.indexOf(">");
			            			y=str.indexOf("<",x);
			        				System.out.print(str.substring(x+1,y).trim()+"\t");	
		                		}
		                		
		            			while (true) {
		            				str=reader.readLine().trim();
		            				if (str.equals("</tr>")==true)
		            					break;
		            				str=reader.readLine().trim();
		            				str=reader.readLine().trim();
		            				if (str.equals("</td>")==true)
		            					str="";
		            				else if (str.charAt(0)>='0' && str.charAt(0)<='9') {
		            					x=str.indexOf("<br>");
		            					if (x!=-1) {
		            						y=str.indexOf(")",x+4);
		            						str=str.substring(0,7)+" "+str.substring(x+4,y+1);
		            						str1=reader.readLine();	//读多一行
		            					}
		            					else
			            					str=str.substring(0,7);
		            				}
		            				else {
		            					x=str.indexOf(")");
		            					str=str.substring(0,x+1);
		            					str1=reader.readLine();	//读多一行
		            				}
		        					System.out.print(str+"\t");
		            			}
	                			System.out.println();
	                		}
//	            			System.exit(0);	//test
	                	}
	            	}
                }
	            else{
	                System.out.println("获取不到网页的源码，服务器响应代码为："+responsecode);
	            }
	        }
	        catch(Exception e){
	        	//End Of Input
	//            System.out.println("获取不到网页的源码,出现异常："+e);
	        }
        }
        

    }
}
/*
	                	p=Pattern.compile("<td>|</td>");
	                	m=p.matcher(str);
	                	str=m.replaceAll("");
	            		System.out.print(str+"\t");
*/