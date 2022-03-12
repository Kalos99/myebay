<!doctype html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Rimuovi Annuncio</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Sei sicuro di voler eliminare questo annuncio?</h5>
					    </div>
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Testo</dt>
							  <dd class="col-sm-9">${ delete_annuncio_attr.testoAnnuncio }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Prezzo:</dt>
							  <dd class="col-sm-9">${ delete_annuncio_attr.prezzo }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data pubblicazione:</dt>
							  <fmt:formatDate value="${delete_annuncio_attr.dataPubblicazione}" type="date" pattern="dd/MM/yyyy" var="theFormattedDate" />
							  <dd class="col-sm-9">${ theFormattedDate }</dd>
					    	</dl>
					
					    	<!-- info Categorie -->
			                <p>
				              <a class="btn btn-outline-primary btn-sm" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
				               	 Info Categorie
				              </a>
				            </p>
				            
				            <div class="collapse" id="collapseExample">
				              <div class="card card-body">
				                 <dl class= "row">
				                  <dt class = "col-sm-3 text-right"> Categorie:</dt>
				                  <c:forEach items="${delete_annuncio_attr.categorie}" var="categoria">
				                    <dd class="row-sm-9">${ categoria.codice }-${categoria.descrizione }</dd>
				                    <br>
				                  </c:forEach>
				                </dl>
				                  
				               </div>
				             <!-- end info Ruoli -->
				             </div>
					    	
					    </div>
					    
					   <form method="post" action="${pageContext.request.contextPath }/annuncio/ExecuteDeleteAnnuncioUtenteServlet" class="row g-3" novalidate="novalidate">
					     	<div class='card-footer'>
					        	<input type="hidden" name="idAnnuncio" value="${delete_annuncio_attr.id}">
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<a href="${pageContext.request.contextPath }/utente/home.jsp" class='btn btn-outline-secondary' style='width:80px'>
					            	<i class='fa fa-chevron-left'></i> Home
					        	</a>
							
					  	  </div>		  
			    	</form>
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>