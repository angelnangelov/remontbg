const offerList = document.getElementById('offerList')
const searchBar = document.getElementById('searchInput')

const allOffers = [];

fetch("http://localhost:8000/offers/api").then(response => response.json()).
then(data => {
    for (let offer of data) {
        allOffers.push(offer);
    }
})

console.log(allOffers);

searchBar.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBar.value.toLowerCase();
    let filteredOffers = allOffers.filter(offer => {
        return offer.name.toLowerCase().includes(searchingCharacters)
            || offer.description.toLowerCase().includes(searchingCharacters);
    });
})


