function check() {
    var excel_file=$("excel_file").val();

    if(excel_file==""||excel_file.length==0)
    {
        alert("请选择文件路径");
        return false;
    }
    else {
        return true;
    }

}


$(function () {




})