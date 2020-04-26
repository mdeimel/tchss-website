var TCHS = TCHS || {};
TCHS.register = TCHS.register || {};

$(document).ready(function() {
    TCHS.register.setupPayment();
    TCHS.register.setupParentRegistration();
});

TCHS.register.setupPayment = function() {
    var stripe = Stripe('pk_test_7RJ54HVvM8J2ooIzpYaynqVf0020Ud9CHY');
    var elements = stripe.elements();

    // Set up Stripe.js and Elements to use in checkout form
    var style = {
        base: {
            color: "#32325d",
        }
    };

    var card = elements.create("card", { style: style });
    card.mount("#card-element");

    card.addEventListener('change', function(event) {
        var displayError = document.getElementById('card-errors');
        if (event.error) {
            displayError.textContent = event.error.message;
        } else {
            displayError.textContent = '';
        }
    });

    // var elements = stripe.elements({
    //     fonts: [
    //         {
    //             cssSrc: 'https://fonts.googleapis.com/css?family=Source+Code+Pro',
    //         },
    //     ],
    //     // Stripe's examples are localized to specific languages, but if
    //     // you wish to have Elements automatically detect your user's locale,
    //     // use `locale: 'auto'` instead.
    //     locale: window.__exampleLocale
    // });
    //
    // // Floating labels
    // var inputs = document.querySelectorAll('.cell.example.example2 .input');
    // Array.prototype.forEach.call(inputs, function(input) {
    //     input.addEventListener('focus', function() {
    //         input.classList.add('focused');
    //     });
    //     input.addEventListener('blur', function() {
    //         input.classList.remove('focused');
    //     });
    //     input.addEventListener('keyup', function() {
    //         if (input.value.length === 0) {
    //             input.classList.add('empty');
    //         } else {
    //             input.classList.remove('empty');
    //         }
    //     });
    // });
    //
    // var elementStyles = {
    //     base: {
    //         color: '#32325D',
    //         fontWeight: 500,
    //         fontFamily: 'Source Code Pro, Consolas, Menlo, monospace',
    //         fontSize: '16px',
    //         fontSmoothing: 'antialiased',
    //
    //         '::placeholder': {
    //             color: '#CFD7DF',
    //         },
    //         ':-webkit-autofill': {
    //             color: '#e39f48',
    //         },
    //     },
    //     invalid: {
    //         color: '#E25950',
    //
    //         '::placeholder': {
    //             color: '#FFCCA5',
    //         },
    //     },
    // };
    //
    // var elementClasses = {
    //     focus: 'focused',
    //     empty: 'empty',
    //     invalid: 'invalid',
    // };
    //
    // var cardNumber = elements.create('cardNumber', {
    //     style: elementStyles,
    //     classes: elementClasses,
    // });
    // cardNumber.mount('#example2-card-number');
    //
    // var cardExpiry = elements.create('cardExpiry', {
    //     style: elementStyles,
    //     classes: elementClasses,
    // });
    // cardExpiry.mount('#example2-card-expiry');
    //
    // var cardCvc = elements.create('cardCvc', {
    //     style: elementStyles,
    //     classes: elementClasses,
    // });
    // cardCvc.mount('#example2-card-cvc');
    //
    // registerElements([cardNumber, cardExpiry, cardCvc], 'example2');
};

/**
 * Only display the parent registration section when a child is registered for
 * the given sport.
 */
TCHS.register.setupParentRegistration = function() {
    // Baseball
    if ($(".register-baseball").length > 0) {
        $("#register-parent-baseball").show();
    }

    // Soccer
    if ($(".register-soccer").length > 0) {
        $("#register-parent-soccer").show();
    }
};