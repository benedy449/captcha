<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sunny.action.Checkpingcode" %>
<%@ page import="com.framework.utils.TypeUtil" %>
<%@ page import="com.framework.constant.Constants" %>
<%@ page import="com.framework.control.ServiceResult" %>
<%@ page import="java.awt.image.BufferedImage" %>

<%@ page import="javax.servlet.ServletOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@page import="java.io.ByteArrayOutputStream"%>


<%
	Map<String,BufferedImage> imageMap = new LinkedHashMap<String,BufferedImage>();
	ServiceResult result = (ServiceResult) request.getAttribute(Constants.ACTION_RESULT);
	imageMap = (Map<String,BufferedImage>)result.getOutputResult();

	pageContext.setAttribute("imageMap", imageMap);
	BufferedImage image = imageMap.get("ckImg");
	
	//System.out.print(imageMap.get("ckImg"));
	
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	ImageIO.write( image, "jpg", baos );
	baos.flush();
	byte[] imageInByteArray = baos.toByteArray();
	baos.close();
	String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
	//<img src="data:image/jpg;base64,<%=b64" />
	
%>
<%=b64 %>
