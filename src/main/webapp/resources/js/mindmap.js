$(document).ready(function () {

    $('#termBox').connections({
        to: '.child'
    });
    
    $('#definitionToggle').click(function () {
        $('#definition').toggle();
        $(this).text(toggleIcon($(this).text()));
        updateConnections();
    });
    
    $('#translationToggle').click(function () {
        $('#translation').toggle();
        $(this).text(toggleIcon($(this).text()));
        updateConnections();
    });
    
    $('#contextToggle').click(function () {
        $('#context').toggle();
        $(this).text(toggleIcon($(this).text()));
        updateConnections();
    });
    
    $('#expertNetworkToggle').click(function () {
        $('#expertNetwork').toggle();
        $(this).text(toggleIcon($(this).text()));
        updateConnections();
    });
    
   $('#resourcesToggle').click(function () {
        $('#resources').toggle();
        $(this).text(toggleIcon($(this).text()));
        updateConnections();
    });

});

function toggleIcon(text) {
    var oldToggleIcon = text.charAt(0);
    var newToggleIcon = (oldToggleIcon === '+') ? '-' : '+';
    return text.replace(oldToggleIcon, newToggleIcon);
}

function updateConnections() {
    $("connection, #termBox").connections("update");
}
