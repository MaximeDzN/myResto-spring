var DEBUG_MODE = true;

//Bulle nombre de produit dans le panier
document.querySelector(".cardCount").innerText = document.querySelectorAll("div.product.no-float").length;


//Ajout d'un produit au panier
let formAddToCard = document.querySelectorAll(".addToCard");
if (DEBUG_MODE) {console.log(formAddToCard);}
formAddToCard.forEach(f => f.addEventListener('submit', event => {
  event.preventDefault();


  var request = new XMLHttpRequest();
  var url = "./addCart";
  request.open("POST", url, true);
  request.setRequestHeader('Content-Type', 'application/json');
  request.onreadystatechange = function () {
      if (request.readyState === 4 && request.status === 200) {
        debugger;
        document.getElementById("modal_card").innerHTML = request.responseText;
        let bubble = document.querySelector(".cardCount");
        bubble.innerText = document.querySelectorAll("div.product.no-float").length;
      }
  };
  let form = event.target;
  let data = new FormData(form);
  let formObj = serialize(data);
  //let formObj = JSON.stringify(data);
  request.send(JSON.stringify(formObj));
}));

//Suppression d'un produit au panier
function card_delete(elem) {
  var request = new XMLHttpRequest();
  var url = "card_delete";
  request.open("POST", url, true);
  request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
  request.onreadystatechange = function () {
      if (request.readyState === 4 && request.status === 200) {
        document.getElementById("modal_card").innerHTML = request.responseText;
        let bubble = document.querySelector(".cardCount");
        bubble.innerText = document.querySelectorAll("div.product.no-float").length;
      }
  };

  request.send("id=" + elem.getAttribute("data"));
}

function serialize (data) {
	let obj = {};
	for (let [key, value] of data) {
		if (obj[key] !== undefined) {
			if (!Array.isArray(obj[key])) {
				obj[key] = [obj[key]];
			}
			obj[key].push(value);
		} else {
			obj[key] = value;
		}
	}
	return obj;
}
