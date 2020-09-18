// Function that changes the image and text of the div container labelled as "Image".
function upDate(previewPic) {

    var imageLink = $(previewPic).attr("src");
    $('#image').css("background-image", "url(" + imageLink + ")");
    var imageText = $(previewPic).attr("alt");
    $('#image').text(imageText);
}

// Function that changes the image and text of the div container to nothing.
function unDo() {

    var noImage = "";
    $('#image').css("background-image", "url(" + noImage + ")");
    $('#image').text(" ");
}
