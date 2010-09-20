$.extend($,
	{
		globalFunction:function(func){
		//make callback Global
		var callbackName="callback_" + Math.floor(Math.random()*10000);
		jQuery.globalEval("var " + callbackName +"=" + func);
		return callbackName;
	},
	
	/**
	 * @param jsFunction a boolean which says whether returns the string 
	 * 		function name or the jsFunction
	 */
	dynamicFunction:function(script,jsFunction){
		//make callback Global
		var callbackName="callback_" + Math.floor(Math.random()*10000);
		var func="function "+ callbackName +"(response){"+script+"} ";
		jQuery.globalEval(func);
		
		if(jsFunction){
			var rtn;
			eval("rtn="+callbackName);
			return rtn;
		}
		else{
			return callbackName;
		}
	},
	
});
