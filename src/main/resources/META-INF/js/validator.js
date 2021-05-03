function Validator(options){

	function getParent(element, selector) {
		while (element.parentElement) {
			if (element.parentElement.matches(selector)) {
				return element.parentElement;
			}
			element = element.parentElement;
		}
	}

	var selectorRules = {};

	//Hàm thực hiện validate
	function validate(inputElement, rule) {

		var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector);
		var errorMessage;

		// lấy ra các rules của selector
		var rules = selectorRules[rule.selector];

		//lặp qua từng rule & kiểm tra
		for (var i = 0; i < rules.length; ++i) {
			errorMessage = rules[i](inputElement.value);
			if(errorMessage) break;
		}

		if (errorMessage){
			errorElement.innerText = errorMessage;
			getParent(inputElement, options.formGroupSelector).classList.add('invalid');
		}
		else {
			errorElement.innerText = '';
			getParent(inputElement, options.formGroupSelector).classList.remove('invalid');
		}
		return !errorMessage;
	}

	//Lấy element của form cần validate
	var formElement = document.querySelector(options.form);
	if (formElement) {

		formElement.onsubmit = function (e){
			e.preventDefault();

			var isFormValid = true;

			options.rules.forEach(function (rule){
				var inputElement = formElement.querySelector(rule.selector);
				var isValid = validate(inputElement, rule);
				if (!isValid) {
					isFormValid = false;
				}
			});

			if(isFormValid) {
				if (typeof options.onSubmit === 'function') {

					var enableInputs = formElement.querySelector('[name]');

					var formValues = Array.form(enableInputs).reduce(function (values, input) {
						values[input.name] = input.value;
						return values;
					}, {});

					options.onSubmit(formValues);
				}
				else {
					formElement.submit();
				}
			}
		}

		options.rules.forEach(function (rule){

			//Lưu rules cho các input
			if (Array.isArray(selectorRules[rule.selector])) {
				selectorRules[rule.selector].push(rule.test);
			}
			else {
				selectorRules[rule.selector] = [rule.test];
			}

			var inputElement = formElement.querySelector(rule.selector);
			
			if(inputElement) {
				//Xử lý trường hợp blue khỏi input
				inputElement.onblur = function (){
					validate(inputElement, rule);
				}

				//Xử lý mỗi khi người dùng nhập vào input
				inputElement.oninput = function (){
					var errorElement = getParent(inputElement, options.formGroupSelector).querySelector(options.errorSelector);
					errorElement.innerText = '';
					getParent(inputElement, options.formGroupSelector).classList.remove('invalid');
				}
			}
		});
	}
}
//Định nghĩa các rule
Validator.isRequired = function (selector, message){
	return {
		selector: selector,
		test: function (value){
			return value.trim() ? undefined : message || 'Vui lòng nhập trường này!'
		}
	};
}


Validator.isPhoneNumber = function (selector, message) {
	return {
		selector: selector,
		test: function (value){
			var regex = /^(0|\+84)(\s|\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\d)(\s|\.)?(\d{3})(\s|\.)?(\d{3})$/;
			return regex.test(value) ? undefined : message || 'Số điện thoại không hợp lệ'
		}
	};
}