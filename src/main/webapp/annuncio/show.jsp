<!doctype html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Visualizza Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Visualizza dettaglio</h5>
					    </div>
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Testo</dt>
							  <dd class="col-sm-9">${ visualizza_annuncio_attr.testoAnnuncio }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Prezzo:</dt>
							  <dd class="col-sm-9">${ visualizza_annuncio_attr.prezzo }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data pubblicazione:</dt>
							  <fmt:formatDate value="${visualizza_annuncio_attr.dataPubblicazione}" type="date" pattern="dd/MM/yyyy" var="theFormattedDate" />
							  <dd class="col-sm-9">${ theFormattedDate }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Creatore Annuncio:</dt>
							  <dd class="col-sm-9">${visualizza_annuncio_attr.utenteInserimento.username}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Categorie:</dt>
							  <c:forEach items="${visualizza_annuncio_attr.categorie}" var="categoria">
							  	<dd class="row-sm-9">${ categoria.codice }</dd>
							  </c:forEach>
					    	</dl>
					    	
					    </div>
					    
					    <div class='card-footer'>
						    <a href="${ pageContext.request.contextPath }/ExecuteCompraServlet" class='btn btn-outline-primary' style='width:100px'>
						            <i class='fa fa-chevron-left'></i> Compra
						        </a>
					        <a href="${ pageContext.request.contextPath }/ExecuteListAnnunciServlet" class='btn btn-outline-secondary' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Back
					        </a>
					    </div>
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>