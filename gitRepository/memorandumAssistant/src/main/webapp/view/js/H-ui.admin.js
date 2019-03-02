/*弹出层*/
function layer_show(w,h,title,url){
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	var offsetTop=($(window).height()-h)/2
	$.layer({
    	type: 2,
    	shadeClose: true,
    	title: title,
		maxmin:false,
		shadeClose: true,
    	closeBtn: [0, true],
    	shade: [0.8, '#000'],
    	border: [0],
    	offset: [offsetTop+'px',''],
    	area: [w+'px', h +'px'],
    	iframe: {src: url}
	});
}

function layer_show2(w,h,title,url){
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	$.layer({
    	type: 2,
    	shadeClose: true,
    	title: title,
		maxmin:false,
		shadeClose: true,
    	closeBtn: [0, true],
    	shade: [0.5, '#000'],
    	border: [0],
    	offset: ['20px',''],
    	area: [w+'px', h +'px'],
    	iframe: {src: url}
	});
}

function layer_show3(w,h,title,url){
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	if (title == null || title == '') {
		title=false;
	};
	if (url == null || url == '') {
		url="404.html";
	};
	var offsetTop=($(window).height()-h)/2
	$.layer({
    	type: 2,
    	close : function(index){
    		location.reload();
    	},
    	shadeClose: true,
    	title: title,
		maxmin:false,
		shadeClose: true,
    	closeBtn: [0, true],
    	shade: [0.8, '#000'],
    	border: [0],
    	offset: [offsetTop+'px',''],
    	area: [w+'px', h +'px'], 
    	iframe: {src: url}
	});
}

function layer_show_custPage (w,h,title,str) {
	if (w == null || w == '') {
		w=800;
	};
	if (h == null || h == '') {
		h=($(window).height() - 50);
	};
	if (title == null || title == '') {
		title=false;
	};
	
	var offsetTop=($(window).height()-h)/2;
	
	$.layer({
    	type: 1,
    	close : function(index){
    		location.reload();
    	},
    	shadeClose: true,
    	title: title,
		maxmin:false,
		shadeClose: true,
    	closeBtn: [0, true],
    	shade: [0.8, '#000'],
    	border: [0],
    	offset: [offsetTop+'px',''],
    	area: [w+'px', h +'px'], 
    	//iframe: {src: url}
    	page : {html:str}
	});
}


/*用户-编辑-保存*/
function user_edit_save(obj,id){
	var i = parent.layer.getFrameIndex();
	parent.layer.close(i);
}
