var selDiv = "";
var storedFiles = [];
var $fileUpload=$("input[type='file']");

$(document).ready(function() {

    $("#files").on("change", function(){
        //$fileUpload = $("input[type='file']");
        //console.log($("input[type='file']").count());

        $fileUpload = $("input[type='file']");
        $fileUpload.rem
        $("input[type='file']").append($fileUpload);


        selDiv = $("#selectedFiles").html("");
        if (parseInt($fileUpload.get(0).files.length)>5){
           alert("You can only upload a maximum of 5 files");
           $fileUpload.val(null);
        }
    });


    //$("#myForm").on("submit", handleForm);

    $("#files").on("change", handleFileSelect);

    $("body").on("click", ".close-btn", removeFile);





});


function handleFileSelect(e) {

    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    console.dir(filesArr);
    //filesArr.pop(filesArr[2]);
    console.log(filesArr);


    filesArr.forEach(function(f) {
        //if(!f.type.match("image.*")) {
        //    storedFiles.push(f);
        //}
        storedFiles.push(f);
        var reader = new FileReader();
        //region Name Formateing
        var name = f.name;
        var ext = name.substring(name.lastIndexOf(".") + 1, name.length);
        var fullName= name.substring(0, name.lastIndexOf(".") + 1);
        if(fullName.length>10)
            fullName=fullName.substring(0,10)+"...";
        else
            fullName=fullName.substring(0,10);
        //endregion
        selDiv = $("#selectedFiles").html("");
        if(f.type.match("image.*")){
            reader.onload = function (e) {
                //var size = f.name/1024;
                var html = "<div class='image-area'> <li> <img src=\""
                    + e.target.result
                    + "\" data-file='"+"' class='selFile' title='Click to remove'>"
                    +"<p class='close-btn'>" +'x'+"</p>" +"<p class='img_text'>" +fullName+ext+"</p>" +"<p class='img_text'>" + formatFileSize(f.size) +"</p>" + "</li>"+"</div>";
                selDiv.append(html);
            }
        }
        else {
            reader.onload = function (e) {
                //var size = f.name/1024;
                //console.log(ext);
                var img_src= ext+'.png';
                //console.log(img_src);

                var html = "<div class='image-area'> <li> <img src='../images/file-type/"+img_src+"'"
                    + e.target.result
                    + "\" data-file='"+"' class='selFile' title='Click to remove'>"
                    +"<p class='close-btn'>" +'x'+"</p>"+"<p class='img_text'>" +fullName+ext+"</p>" +"<p class='img_text'>" + formatFileSize(f.size) +"</p>" + "</li>"+"</div>";
                selDiv.append(html);

            }
        }

        reader.readAsDataURL(f);
    });

}
function removeFile(e) {
    var file = $(this).data("file");
    for(var i=0;i<storedFiles.length;i++) {
        if(storedFiles[i].name === file) {
            storedFiles.splice(i,1);
            break;
        }
    }
    $(this).parent().parent().remove();
}

function formatFileSize(bytes) {
    if (typeof bytes !== 'number') {
        return '';
    }

    if (bytes >= 1000000000) {
        return (bytes / 1000000000).toFixed(2) + ' GB';
    }

    if (bytes >= 1000000) {
        return (bytes / 1000000).toFixed(2) + ' MB';
    }

    return (bytes / 1000).toFixed(2) + ' KB';
}



// Get File Type/Extention

//function getExtention(){
//extension.value=files.value.split('.')[1];
//}


