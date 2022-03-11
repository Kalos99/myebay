<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Aggiornamento utente</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Inserisci i dati per l'aggiornamento dell'utente</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form method="post" action="${pageContext.request.contextPath }/utente/ExecuteEditUtenteServlet" class="row g-3" novalidate="novalidate">
								
								<div class="col-md-6">
									<label for="username" class="form-label">Username <span class="text-danger">*</span></label>
									<input type="text" name="username" id="username" class="form-control" placeholder="Inserire lo username" value="${utenteCheSiVuoleAggiornare.username }">
								</div>
								
								<div class="col-md-6">
									<label for="nome" class="form-label">Nome <span class="text-danger">*</span></label>
									<input type="text" name="nome" id="nome" class="form-control" placeholder="Inserire il nome" value="${utenteCheSiVuoleAggiornare.nome }">
								</div>
								
								<div class="col-md-6">
									<label for="cognome" class="form-label">Cognome <span class="text-danger">*</span></label>
									<input type="text" name="cognome" id="cognome" class="form-control" placeholder="Inserire il cognome" value="${utenteCheSiVuoleAggiornare.cognome }">
								</div>
								
								<div class="col-md-3">
									<label for="stato" class="form-label">Stato <span class="text-danger">*</span></label>
								    <select class="form-select" id="stato" name="stato" required>
								    	<option value="" selected> - Selezionare - </option>
								      	<option value="CREATO" ${utenteCheSiVuoleAggiornare.stato == 'CREATO'?'selected':''} >CREATO</option>
								      	<option value="ATTIVO" ${utenteCheSiVuoleAggiornare.stato == 'ATTIVO'?'selected':''} >ATTIVO</option>
								    </select>
								</div>
							      
							      <c:forEach items="${mappaRuoliConSelezionati_attr}" var="ruoloEntry">
										<div class="form-check">
											  <input class="form-check-input" name="ruoli" type="checkbox" value="${ruoloEntry.key.id}" id="ruoli-${ruoloEntry.key.id}" ${ruoloEntry.value?'checked':'' }>
											  <label class="form-check-label" for="ruoli-${ruoloEntry.key.id}" >
											    ${ruoloEntry.key.codice}
											  </label>
										</div>
								  	</c:forEach>
							
								<div class="col-12">
									<input type="hidden" name="idUtente" value="${utenteCheSiVuoleAggiornare.id}">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Aggiorna</button>
									<a href="ExecuteListRegistaServlet" class='btn btn-outline-secondary' style='width:80px'>
					            		<i class='fa fa-chevron-left'></i> Back
					       			</a>
							   </div>
						</form>
  
				    
				    
					<!-- end card-body -->			   
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