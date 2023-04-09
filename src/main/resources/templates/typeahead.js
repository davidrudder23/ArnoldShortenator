$(document).ready(function(){
    $('.typeahead').keyup(function() {
        getMatches($(this).val())
    })
});

function getMatches(slug) {

    $.ajax({
        url:"/api/search/"+slug,
        success: function(data) {
            console.log(data);
            var results = "";
            var foundExactMatch = false;
            $("#add-button").hide();
            $.each(data, function(index, value) {
                console.log(value.fullSourceURL);
                console.log(value.destinationURL);
                if (value.slug == slug) {
                    foundExactMatch = true;
                }
                results += `
                        <div class="col-lg-6">
                                        <h4>`+value.fullSourceURL+`</h4>
                                        <p>`+value.destinationURL+`
                                    </div>
                                    `;
            });

            $("#typeahead-results").html(results);

            if (!foundExactMatch) {
                $("#add-button").show();
            }
        }
    })
}

