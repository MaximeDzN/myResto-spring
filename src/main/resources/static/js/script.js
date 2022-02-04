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
        document.getElementById("modal_card").querySelector("div.relative").innerHTML += '<div class="product no-float">' +
           '<div class="product-body">' +
             '<img src="' + f.parentNode.querySelector("img").getAttribute("src") + '" alt="">' +
             '<span data="' + f.parentNode.querySelector("span").innerText.replace(" €", "") + '">' + f.parentNode.querySelector("p").innerText + '<b> x1</b></span>' +
           '</div>' +
           '<img src="./img/delete.svg" alt="" class="delete-product" data="" onclick="card_delete(this)">' +
         '</div>';
        let bubble = document.querySelector(".cardCount");
        bubble.innerText = document.querySelectorAll("div.product.no-float").length;
      }
  };
  let form = event.target;
  let data = new FormData(form);

  let formObj = serialize(data);
  formObj = JSON.stringify(formObj);
  formObj = JSON.parse(formObj);

  var myObj = {
        'item': {
          'id': formObj.id
        },
        'quantity' : formObj.quantity
    };
  request.send(myObj);
}));

//Suppression d'un produit au panier
function card_delete_(elem) {
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

function refreshPrice() {
    let sum = 0;
    debugger;
    document.getElementById("modal_card").querySelectorAll("span[data]").forEach(function (elem) {
        sum += parseInt(elem.getAttribute("data"));
    })
    document.getElementById("total_cart").innerText = "Total : " + sum.toFixed(1) + "€ TTC";
}

function card_delete(elem) {
    elem.parentNode.remove();
    refreshPrice();
}