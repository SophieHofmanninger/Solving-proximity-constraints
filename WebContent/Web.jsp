<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="userInterfaces.WebInterface"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Unification</title>
</head>
<body>
<div style="background-color:#c7d8eb;">
<h1>Solving proximity constraints</h1>
<h3>Developed by Jan-Michael Holzinger &amp; Sophie Hofmanninger</h3>
Version 1.0

</div>
<br>
This is a Java implementation of the algorithm pre-Unification and Constraint Simplification described in:<br>
<em>Solving proximity constraints (Temur Kutsia and Cleo Pau)</em><br>
<br>
<strong>Input Syntax:</strong><br>
<ul>
	<li> The symbols [a-t] should be used for function names and [u-z] for variable names. </li>
	<li> The unification problem can be written with = or =?. </li>
	<li> Several unification problems can be entered, by splitting them with a semicolon (;). If more than one problem will be entered, only the first one will be solved. </li>
	<li> The user is assumed to make a correct input (correct number of parenthesis ,...).</li>
</ul>
<br>
<br>

<form action="Web.jsp" method="POST">

<%
if(request.getParameter("expression")!=null){
	WebInterface.onlyWI.setProblems(request.getParameter("functions"));
	WebInterface.onlyWI.setSilent(request.getParameter("mode").compareTo("silent")==0);
	WebInterface.onlyWI.setLambda(Float.parseFloat(request.getParameter("lambda")));
%>

<table border="1">
<tr>
<td>Expression</td>
<td>
<input type="text" name="functions" value="<%= WebInterface.onlyWI.getFunString()%>" readonly>
</td>
</tr>
<tr>
<td>Arity</td>
<td>additional Functions</td>
</tr>
<%
for(int i=0;i<=WebInterface.onlyWI.getMaxArity();i++){
%>
<tr>
<td>
<%= i %>:
</td>
<td>
<input type="number" name="add<%= i %>" value="0" step="1" required>
</td>
</tr>
<%
}
%>
</table>
	
<%
	if(!WebInterface.onlyWI.getError()){
%>
<input type="Submit" name="arity" value="Enter additional Functions">	
<%
}
}
else if(request.getParameter("arity")!=null){
	
	for(int i=0;i<=WebInterface.onlyWI.getMaxArity();i++){
		String name = "add"+i;
		WebInterface.onlyWI.setAddArity(i, Integer.parseInt(request.getParameter(name)));
	}	
%>

<table border="1">
<tr>
<td>Expression</td>
<td>
<input type="text" name="functions" value="<%= WebInterface.onlyWI.getFunString()%>" readonly>
</td>
</tr>
<tr>
<td>Arrity</td>
<td>Matrix</td>
</tr>
<%
for(int arr=0;arr<=WebInterface.onlyWI.getMaxArity();arr++){
%>	
<tr>
<td><%= arr %>:</td>
<td>
<table>
<%
for(int row=0;row<=WebInterface.onlyWI.getAllCountAtArity(arr);row++){
%>
<tr>
<%	
	for(int col=0;col<=WebInterface.onlyWI.getAllCountAtArity(arr);col++){
%>
<td>
<%
		if(row==col){
%>
<input type="number" value="1.0" readonly>
<%
		}
		else if(row==0 && col<=WebInterface.onlyWI.getFunCountAtArity(arr)){
%>
<input type="text" name="fun-<%=arr+"-"+row+"-"+col %>" value="<%= WebInterface.onlyWI.getFunAtArity(arr).get(col-1) %>" readonly>
<%
		}
		else if(row==0){
%>
<input type="text" name="fun-<%=arr+"-"+row+"-"+col %>" required>
<%
		}
		else if(col==0 && row<=WebInterface.onlyWI.getFunCountAtArity(arr)){
%>
<input type="text" name="fun-<%=arr+"-"+row+"-"+col %>" value="<%= WebInterface.onlyWI.getFunAtArity(arr).get(row-1) %>" disabled>
<%
		}
		else if(col==0){
%>
<input type="text" name="fun-<%=arr+"-"+row+"-"+col %>" value="like in Col" disabled>
<%
		}
		else if(col>row){
%>			
<input type="number" min="0" max="1" value="0.0" name="field-<%=arr+"-"+row+"-"+col %>" step="0.01" required>
<%		
		}
		else{
%>
<input type="text" value="-" disabled>
<%
		}
%>
</td>
<%		
	}
%>
</tr>
<%
}
%>
</table>
</td>
</tr>
<%
}
%>

</table>

<%
	if(!WebInterface.onlyWI.getError()){
%>
<input type="Submit" name="matrix" value="Enter Matrix" required>	

<%
}
}
else if(request.getParameter("matrix")!=null){
	for(int arr=0;arr<=WebInterface.onlyWI.getMaxArity();arr++){
		int maxRowCol = WebInterface.onlyWI.getAllCountAtArity(arr);
		for(int row=1;row<=maxRowCol;row++){
			String f1 = "fun-"+arr+"-0-"+row;	
			f1 = request.getParameter(f1);			
			for(int col=1;col<=maxRowCol;col++){
				String f2 = "fun-"+arr+"-0-"+col;
				f2 = request.getParameter(f2);
				if(row<col){
					String name="field-"+arr+"-"+row+"-"+col;
					WebInterface.onlyWI.addRelation(f1, f2, Float.parseFloat(request.getParameter(name)));
				}
				
			}
		}
	}
	WebInterface.onlyWI.closeOpenCases();
	
%>

<table border="1">
<tr>
<td>Expression</td>
<td>
<input type="text" name="functions" value="<%= WebInterface.onlyWI.getFunString()%>" readonly>
</td>
</tr>
<tr>
<td>Output</td>
<td>
	<%= WebInterface.onlyWI.getSolution() %>
</td>
</tr>
</table>
	
<%
}
else{
	if(request.getParameter("reset")!=null){
		WebInterface.onlyWI.reset();
	}
	
	%>
<table border="1">
<tr>
<td>Expression</td>
<td>
<input type="text" name="functions" required>
</td>
</tr>
<tr>
<td>Mode</td>
<td>
<select name="mode" required>
<option value="silent" selected>Silent</option>
<option value="detailed">Detailed</option>
</select>
</td>
</tr>
<tr>
<td>Lambda</td>
<td>
<input type="number" name="lambda" min="0.0" max="1.0" step="0.01" value="0.5" required>
</td>
</tr>
</table>
	
<input type="Submit" name="expression" value="Enter Function">	
	<%
}
%>

<input type="Submit" name="reset" value="Reset">

</form>
<br>
<br>
<strong>Examples:</strong><br>
<ul>
	<li><p>p(x,z)=?q(f(b),f(x)) <br>
		lambda=0.5 <br> 
		R = {(a, a0), (a0, b), (b, c0), (c0, c), (p, q)} <br> 
		 </p>  </li>
	<li><p>p(x,y,x)=q(f(a),g(d),y) <br>
	    lambda=0.2 <br>
	    R={(a,b),(b,c),(c,d),(a,b'),(b',c'),(c',d),(f,g),(p,q)}</p> </li>
</ul>
<br>

</body>
</html>