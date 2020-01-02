
var currPic = 1;
var totPics = 3;
function setupPicChange() {
	keepTimer = setTimeout("changePic();", 3500);
}
function changePic() {
	change();
	setupPicChange();
}
function change() {
	currPic++;
	if (currPic > totPics)
		currPic = 1;
	document.getElementById("slider").style.backgroundImage = "url(../utils/images/header"
			+ currPic + ".jpg)";
}

function stopTimer() {
	clearTimeout(keepTimer);
}

function validarEnteros(evt) {
	keynum = (document.all) ? evt.keyCode : evt.which;
	// ESPACIO=32
	// BORRAR=8
	// ENTER=13
	//alert("keynum " + keynum);
	if ((keynum > 47 && keynum < 58) || keynum == 8 || keynum == 13) {
		return true;
	} else {
		return false;
	}
}

function validarNombres(evt) {
	keynum = (document.all) ? evt.keyCode : evt.which;
	// ESPACIO=32
	// BORRAR=8
	// ENTER=13
	// alert("keynum "+keynum);
	// digitos
	if (!(keynum > 47 && keynum < 58) || keynum == 8 || keynum == 13
			|| keynum == 32) {
		return true;
	} else {
		return false;
	}
}