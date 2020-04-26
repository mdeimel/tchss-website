var TCHS = TCHS || {};
TCHS.register = TCHS.register || {};

$(document).ready(function() {
    TCHS.register.setupToggleButtons();
});

TCHS.register.setupToggleButtons = function() {
    $(".btn-toggle").click(function() {
        var $this = $(this);
        if ($this.hasClass("btn-outline-secondary")) {
            $this.removeClass("btn-outline-secondary");
            $this.addClass("btn-success");
            $this.text("Yes");
            $this.next().val("true");
        }
        else {
            $this.removeClass("btn-success");
            $this.addClass("btn-outline-secondary");
            $this.text("No");
            $this.next().val("false");
        }
    });
};