const offerList = document.getElementById('offerList')
const searchBar = document.getElementById('searchInput')

const allOffers = [];

fetch("http://localhost:8000/offers/api").then(response => response.json()).then(data => {
    for (let offer of data) {
        allOffers.push(offer);
    }
})

console.log(allOffers);

searchBar.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBar.value.toLowerCase();
    let filteredOffers = allOffers.filter(offer => {
        return offer.name.toLowerCase().includes(searchingCharacters)
            || offer.description.toLowerCase().includes(searchingCharacters)
            || offer.category.toLowerCase().includes(searchingCharacters)
            || offer.region.toLowerCase().includes(searchingCharacters);
    });
    displayOffers(filteredOffers);

})

const displayOffers = (offers) => {
    offerList.innerHTML = offers
        .map((offer) => {
            return `             <div class="col-xs-12 col-sm-6 col-md-3" >

                <div class="col-item" id="offerList">
                    <p class="font-weight-bolder text-sm-center"> ${offer.name}</p>
                    <div class="col-item">
                    <p class="font-weight-bolder text-sm-center">Категория:  ${offer.category}</p>
                    <div class="photo">
                        <img  src="${offer.image}" class="img-responsive" alt="a" />
                    </div>
                    <div class="info">
                        <div class="row">
                            <div class="price col-md-5">
                                <h5>${offer.region} </h5>
                                <p class="separator text-danger text-sm-center" >Цена:${offer.price}</p>
                            </div>
                            <div class="rating hidden-sm col-md-5">
                            </div>
                        </div>

                        <div class="separator clear-left">
                        <p class="btn-add" >Тел:${offer.ownerPhoneNumber}
                            <i class="fa fa-shopping-cart"></i>a</p>
                               <p class="btn-details">
                            <i class="fa fa-list"></i><a href="/offer/single-offer/${offer.id}" class="hidden-sm">Още детайли</a></p>
                       
                    </div>
                        <div class="clearfix">
                        </div>
                        </div>
                        </div>
                        </div>
    
                </div>`
        })
        .join('');

}
