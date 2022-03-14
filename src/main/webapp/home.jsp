<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="./header.jsp" />
		<!-- Custom styles per le features di bootstrap 'Columns with icons' -->
	   <link href="${pageContext.request.contextPath}/assets/css/features.css" rel="stylesheet">
	   
	   <title>Homepage</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   		
	   		<!-- #####################################  -->
	   		<!-- elementi grafici per le features in basso  -->
	   		<!-- #####################################  -->
	   		<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
			  <symbol id="people-circle" viewBox="0 0 16 16">
			    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
			    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
			  </symbol>
			  <symbol id="collection" viewBox="0 0 16 16">
			    <path d="M2.5 3.5a.5.5 0 0 1 0-1h11a.5.5 0 0 1 0 1h-11zm2-2a.5.5 0 0 1 0-1h7a.5.5 0 0 1 0 1h-7zM0 13a1.5 1.5 0 0 0 1.5 1.5h13A1.5 1.5 0 0 0 16 13V6a1.5 1.5 0 0 0-1.5-1.5h-13A1.5 1.5 0 0 0 0 6v7zm1.5.5A.5.5 0 0 1 1 13V6a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-.5.5h-13z"/>
			  </symbol>
			  <symbol id="toggles2" viewBox="0 0 16 16">
			    <path d="M9.465 10H12a2 2 0 1 1 0 4H9.465c.34-.588.535-1.271.535-2 0-.729-.195-1.412-.535-2z"/>
			    <path d="M6 15a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm0 1a4 4 0 1 1 0-8 4 4 0 0 1 0 8zm.535-10a3.975 3.975 0 0 1-.409-1H4a1 1 0 0 1 0-2h2.126c.091-.355.23-.69.41-1H4a2 2 0 1 0 0 4h2.535z"/>
			    <path d="M14 4a4 4 0 1 1-8 0 4 4 0 0 1 8 0z"/>
			  </symbol>
			  <symbol id="chevron-right" viewBox="0 0 16 16">
			    <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
			  </symbol>
			</svg>
			<!-- ############## end ###################  -->
	   
	   
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="./navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  	<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
				  ${errorMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
				<div class="alert alert-success alert-dismissible fade show ${successMessage==null?'d-none':'' }" role="alert">
				  ${successMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
			    
			     <div class="p-5 mb-4 bg-light rounded-3">
				      <div class="container-fluid py-5">
				        <h1 class="display-5 fw-bold">Bentornato ${userInfo.username }</h1>
				        <p class="col-md-8 fs-4">Cerca l'annuncio che fa per te o gestisci il tuo profilo </p>
				      </div>
			    </div>
			    
			    			  <div class="p-5 mb-4 bg-light rounded-3">
				      <div class="container-fluid py-5">
					  <div class='card'>
							    <div class='card-header'>
							        <h5>Ricerca annunci</h5> 
							    </div>
							    <div class='card-body'>
					
										<form method="post" action="${pageContext.request.contextPath }/ExecuteSearchAnnunciServlet" class="row g-3" >
										
											<div class="col-md-6">
												<label for="testo" class="form-label">Testo </label>
												<input type="text" class="form-control" name="testo" id="testo" placeholder="Inserire il testo"  >
											</div>
										
											<div class="col-md-6">
												<label for="prezzo" class="form-label">Prezzo </label>
												<input type="number" class="form-control" name="prezzo" id="prezzo" placeholder="Inserire il prezzo" >
											</div>
											
											<c:forEach items="${mappaCategorieConSelezionati_attr}" var="categoriaEntry">
													<div class="form-check">
														  <input class="form-check-input" name="categorie" type="checkbox" value="${categoriaEntry.key.id}" id="ruoli-${categoriaEntry.key.id}" ${categoriaEntry.value?'checked':'' }>
														  <label class="form-check-label" for="categorie-${categoriaEntry.key.id}" >
														    ${categoriaEntry.key.codice}
														  </label>
													</div>
											  	</c:forEach>
											
										<div class="col-6">
											<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
											<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
										</div>
					
									</form>
			  
							    
							    
								<!-- end card-body -->			   
							    </div>
							<!-- end card -->
							</div>
						 </div>
					</div>
			    </div>	
			  
			  <!--  features di bootstrap 'Columns with icons'  -->
			  <div class="container px-4 py-5" id="featured-3">
			    <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
			      <div class="feature col">
			        <div class="feature-icon bg-success bg-gradient">
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#collection"/></svg>
			        </div>
			        <h2>Gestisci i tuoi annunci</h2>
			        <p>Puoi cercare i tuoi annunci, inserire nuovi annunci e aggiornare o eliminare annunci non chiusi.</p>
			        <a href="${pageContext.request.contextPath }/annuncio/PrepareSearchAnnunciUtenteServlet" class="icon-link">
			          Vai alla funzionalità
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#chevron-right"/></svg>
			        </a>
			      </div>
			      <div class="feature col">
			        <div class="feature-icon bg-success bg-gradient">
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#people-circle"/></svg>
			        </div>
			        <h2>Visualizza i tuoi acquisti</h2>
			        <p>Visualizza gli acquisti effettuati durante la tua esperienza sulla nostra piattaforma.</p>
			        <a href="${pageContext.request.contextPath }/acquisto/PrepareSearchAcquistiServlet" class="icon-link">
			          Vai alla funzionalità
			          <svg class="bi" width="1em" height="1em"><use xlink:href="#chevron-right"/></svg>
			        </a>
			      </div>
			      <c:if test="${userInfo.isAdmin() }"> --%>
				      <div class="feature col">
				        <div class="feature-icon bg-success bg-gradient">
				          <svg class="bi" width="1em" height="1em"><use xlink:href="#toggles2"/></svg>
				        </div>
				        <h2>Gestione utenze</h2>
				        <p>Puoi aggiungere, ricercare, modificare oppure disabilitare un utente.</p>
				        <a href="${pageContext.request.contextPath }/utente/PrepareSearchUtenteServlet" class="icon-link">
				          Vai alla funzionalità
				          <svg class="bi" width="1em" height="1em"><use xlink:href="#chevron-right"/></svg>
				        </a>
				      </div>
				    </div>
< 				 </c:if>
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="./footer.jsp" />
	  </body>
</html>