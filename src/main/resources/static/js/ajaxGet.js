



$(function(){


    //  get 请求
    // $.ajax({
    //     async:false,
    //     cache:false,
    //     type:"get",
    //     data:{dj:"如果我是dj"},
    //     url:"/ajaxGet",
    //     dataType:"text",
    //     error:function () {
    //         alert("失败");
    //     },
    //     success:function (data) {
    //         $("#ajaxGet").text(data);
    //     }
    // });



    // post请求
    // $.ajax({
    //     async:true,
    //     cache:false,
    //     type:"post",
    //     data:{dj:"如果我是dj"},
    //     url:"/ajaxGet1",
    //     dataType:"text",
    //     error:function () {
    //         alert("失败");
    //     },
    //     success:function (data) {
    //         $("#ajaxGet").text(data);
    //     }
    // });



var params={
    name:"tuya"
}


    $.ajax({
        async:true,
        cache:false,
        type:"post",
        data:JSON.stringify(params),
        url:"/ajaxGet2",
        //  自动把返回的json字符串转换为Json对象
        dataType:"json",

        // 告诉后台我传给你的是json字符串
        contentType:"application/json",

        error:function () {
            alert("失败");
        },
        success:function (data) {

            var hehe=data.name;
            $("#ajaxGet").text(data.name);
        }




    });

})









