<!doctype html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Rimuovi Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Sei sicuro di voler disabilitare questo utente?</h5>
					    </div>
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Nome</dt>
							  <dd class="col-sm-9">${ delete_utente_attr.nome }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Cognome:</dt>
							  <dd class="col-sm-9">${ delete_utente_attr.cognome  }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Username:</dt>
							  <dd class="col-sm-9">${ delete_utente_attr.username }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data creazione utente:</dt>
							  <fmt:formatDate value="${delete_utente_attr.dateCreated}" type="date" pattern="dd/MM/yyyy" var="theFormattedDate" />
							  <dd class="col-sm-9">${ theFormattedDate }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Stato:</dt>
							  <dd class="col-sm-9">${delete_utente_attr.stato}</dd>
					    	</dl>
					    	
					    	<div class="collapse" id="collapseExample">
				              <div class="card card-body">
				                 <dl class= "row">
				                  <dt class = "col-sm-3 text-right"> Ruoli:</dt>
				                  <c:forEach items="${delete_utente_attr.ruoli}" var="ruolo">
				                    <dd class="row-sm-9">${ ruolo.codice }-${ruolo.descrizione }</dd>
				                    <br>
				                  </c:forEach>
				                </dl>
				                  
				               </div>
				             <!-- end info Ruoli -->
				             </div>
					    	
					    </div>
					    
					    <form method="post" action="${pageContext.request.contextPath }/utente/ExecuteDeleteUtenteServlet" class="row g-3" novalidate="novalidate">
					     	<div class='card-footer'>
					        	<input type="hidden" name="idUtente" value="${delete_utente_attr.id}">
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<a href="${pageContext.request.contextPath }/utente/ExecuteListUtentiServlet" class='btn btn-outline-secondary' style='width:80px'>
					            	<i class='fa fa-chevron-left'></i> Back
					        	</a>
							
					  	  </div>
						<!-- end card -->			  
			    	</form>
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>