<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title> ${voucher.lable}</title>
<#--    <style>
html{line-height:1.15;-webkit-text-size-adjust:100%;}body{margin:0;}main{display:block;}h1{font-size:2em;margin:0.67em 0;}hr{box-sizing:content-box;height:0;overflow:visible;}pre{font-family:monospace,monospace;font-size:1em;}a{background-color:transparent;}abbr[title]{border-bottom:none;text-decoration:underline;text-decoration:underline dotted;}b,strong{font-weight:bolder;}code,kbd,samp{font-family:monospace,monospace;font-size:1em;}small{font-size:80%;}sub,sup{font-size:75%;line-height:0;position:relative;vertical-align:baseline;}sub{bottom:-0.25em;}sup{top:-0.5em;}img{border-style:none;}button,input,optgroup,select,textarea{font-family:inherit;font-size:100%;line-height:1.15;margin:0;}button,input{overflow:visible;}button,select{text-transform:none;}button,[type="button"],[type="reset"],[type="submit"]{-webkit-appearance:button;}button::-moz-focus-inner,[type="button"]::-moz-focus-inner,[type="reset"]::-moz-focus-inner,[type="submit"]::-moz-focus-inner{border-style:none;padding:0;}button:-moz-focusring,[type="button"]:-moz-focusring,[type="reset"]:-moz-focusring,[type="submit"]:-moz-focusring{outline:1px dotted ButtonText;}fieldset{padding:0.35em 0.75em 0.625em;}legend{box-sizing:border-box;color:inherit;display:table;max-width:100%;padding:0;white-space:normal;}progress{vertical-align:baseline;}textarea{overflow:auto;}[type="checkbox"],[type="radio"]{box-sizing:border-box;padding:0;}[type="number"]::-webkit-inner-spin-button,[type="number"]::-webkit-outer-spin-button{height:auto;}[type="search"]{-webkit-appearance:textfield;outline-offset:-2px;}[type="search"]::-webkit-search-decoration{-webkit-appearance:none;}::-webkit-file-upload-button{-webkit-appearance:button;font:inherit;}details{display:block;}summary{display:list-item;}template{display:none;}[hidden]{display:none;}body{max-width:640px;margin:0 auto;}.page{background-color:#f7f8fa;padding-bottom:calc(30rpx + env(safe-area-inset-bottom));}.panel-status{padding:25px 12px 40px 12px;color:#fff;background:linear-gradient(228deg,#ff4343,#ff8243);}.panel-status .panel-status-title{font-weight:500;font-size:20px;line-height:20px;}.panel-status .panel-status-desc{display:flex;align-items:center;margin-top:10px;font-size:12px;}.panel-info{position:relative;margin-top:-60rpx;margin-bottom:20rpx;}.panel-info .panel-info-item{background:#fff;border-radius:8px;margin:12px;}.panel-info .qrcode{padding:15px 14px;display:flex;align-items:center;justify-content:center;flex-direction:column;}.panel-info .qrcode image{width:200px;height:200px;}.panel-list{padding:8px 12px;}.panel-list .panel-list-item{display:flex;-webkit-box-pack:justify;justify-content:space-between;padding:10rpx 0;line-height:1.5;font-size:12px;}.panel-list .panel-list-item .panel-list-item_desc{font-size:14px;color:#999999;}.panel-list .panel-list-item .panel-list-item_detail{color:#333333;font-weight:500;}.qrcode{margin-top:-30px;margin-bottom:10px;border-top-left-radius:8px;border-top-right-radius:8px;}

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

</head>
<body>

<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading text-center">${voucher.lable}</div>
    <div class="panel-body">
        <image class = "center-block" src="${qrcode}"></image>

        <table class="table table-hover" id="dev-table">
            <thead>

            </thead>
            <tbody>
            <tr>
                <td>编号</td>
                <td>${voucher.code}</td>
            </tr>
            <tr>
                <td>类型：</td>
                <td>${voucher.type}</td>
            </tr>
            <tr>
                <td>状态</td>
                <td>${voucher.status_text}</td>
            </tr>
            <tr>
                <td>过期日期</td>
                <td>${voucher.expiry_datetime}</td>
            </tr>
            <tr>
                <td>持卡人</td>
                <td>${voucher.expiry_datetime}</td>
            </tr>

            <#if voucher.holder?? >
                <tr>
                    <td>持卡人</td>
                    <td>${voucher.holder.name}</td>
                </tr>
                <tr>
                    <td>持卡人</td>
                    <td>${voucher.holder.identity}</td>
                </tr>
                <tr>
                    <td>持卡人</td>
                    <td>${voucher.holder.phoneNumber}</td>
                </tr>
            </#if>

            </tbody>
        </table>

    </div>

    <!-- Table -->

</div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.jsdelivr.cn/npm/jquery@1.12.4/dist/jquery.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
</body>
</html>