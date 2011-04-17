<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="<spring:url value="/css/blueprint/screen.css" htmlEscape="true" />" media="screen, projection" />
    <link rel="stylesheet" href="<spring:url value="/css/blueprint/print.css" htmlEscape="true" />" type="text/css" media="print" />
    <!--[if lt IE 8]>
	<link rel="stylesheet" href="<spring:url value="/css/blueprint/ie.css" htmlEscape="true" />" type="text/css" media="screen, projection" />
    <![endif]-->
    <link rel="stylesheet" href="<spring:url value="/css/blueprint/plugins/fancy-type/screen.css" htmlEscape="true" />" type="text/css" media="screen, projection" />
    <link rel="stylesheet" href="<spring:url value="/css/blueprint/plugins/buttons/screen.css" htmlEscape="true" />" type="text/css" media="screen, projection" />
    <link rel="stylesheet" href="<spring:url value="/css/front.css" htmlEscape="true" />" type="text/css" media="screen, projection" />
    <title>~S2NDBRN~</title>
</head>

<body>
	<div class="main">
		<div id="content">
			<div id="header">
				<div id="header-logo" class="left"><a href="<spring:url value="/" />">S2NDBRN</a></div>
				<div class="login-bt-container">
 					<a class="login" href="<spring:url value="/login" htmlEscape="true" />"><spring:message code="header.logIn" /></a>
				</div>
			</div>
			<div id="subheader">
				<div id="subheader-slogan" class="left"><spring:message code="header.subtitle" /></div>
				<div class="right">
					<spring:message code="header.dateFormat" var="dateFormat" />
					<c:set var="today" value="<%=new java.util.Date()%>" />
  					<fmt:formatDate value="${today}" pattern="${dateFormat}" />
  				</div>
			</div>
				<div class="right language-container">
					<a href="?lang=en_US"><img src="<spring:url value="/images/lang/en_US.png" htmlEscape="true" />" title="English (United States)" alt="EN" /></a>&nbsp;
					<a href="?lang=fr_FR"><img src="<spring:url value="/images/lang/fr_FR.png" htmlEscape="true" />" title="Fran&ccedil;ais (France)" alt="FR" /></a>
				</div>
				<div class="container">