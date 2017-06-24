var saveSelectColor = {
    'Name': 'SelcetColor',
    'Color': 'theme-black'
}

// 判断用户是否已有自己选择的模板风格
var userSelectColor = storageLoad('SelcetColor');
if (userSelectColor) {
    $('body').attr('class', userSelectColor.Color)
} else {
    storageSave(saveSelectColor);
    $('body').attr('class', 'theme-black');//默认黑色主题
}


// 本地缓存
function storageSave(objectData) {
    localStorage.setItem(objectData.Name, JSON.stringify(objectData));
}

function storageLoad(objectName) {
    if (localStorage.getItem(objectName)) {
        return JSON.parse(localStorage.getItem(objectName))
    } else {
        return false
    }
}