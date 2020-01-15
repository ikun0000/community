
function selectTag(self) {
    var tagInput = $("#tag");

    if ($(tagInput).val() == "") {
        $(tagInput).val($(self).text().trim());
    } else {
        $(tagInput).val($(tagInput).val() + "," + $(self).text().trim());
    }
    $(self).hide();
}

function toggleTags() {
    $("#tags").slideToggle();
}