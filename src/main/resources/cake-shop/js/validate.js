
function isValidUsername(str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone(val) {
  if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
    return false
  } else {
    return true
  }
}

function isCellUsername(val) {
  if (!/^[\u4e00-\u9fa5A-Za-z0-9-\_]+$/.test(val)) {
    return false
  } else {
    return true
  }
}

function isCellEmail(val) {
  if (!/^[0-9A-Za-z_]+([-+.][0-9A-Za-z_]+)*@[0-9A-Za-z_]+([-.][0-9A-Za-z_]+)*\.[0-9A-Za-z_]+([-.][0-9A-Za-z_]+)*$/
  .test(val)) {
    return false
  } else {
    return true
  }
}

//校验账号
function checkUserName(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入账号"))
  } else if (!isCellUsername(value)) {
    callback(new Error("账号应为英文,数字,下划线和中划线构成"))
  } else if (value.length > 20 || value.length < 6) {
    callback(new Error("账号长度应是6-20"))
  } else {
    callback()
  }
}

//校验密码
function checkPassWord(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入密码"))
  } else if (value.length > 20 || value.length < 6) {
    callback(new Error("密码长度应是6-20"))
  } else {
    callback()
  }
}

//校验网址
function checkUrl(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入图片地址"))
  } else if (value.length > 300 || value.length < 6) {
    callback(new Error("地址长度应是6-300"))
  } else {
    callback()
  }
}

//校验姓名
function checkName(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入姓名"))
  } else if (value.length > 12) {
    callback(new Error("姓名长度应是1-12"))
  } else {
    callback()
  }
}

//校验邮箱
function checkEmail(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入邮箱"))
  } else if (!isCellEmail(value)) {
    callback(new Error("邮箱格式不正确"))
  } else if (value.length > 20 || value.length < 6) {
    callback(new Error("邮箱长度应是6-64"))
  } else {
    callback()
  }
}

//校验手机号
function checkPhone(rule, value, callback) {
  // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
  if (value == null) {
    callback(new Error("请输入手机号"))
  } else if (!isCellPhone(value)) {//引入methods中封装的检查手机格式的方法
    callback(new Error("请输入正确的手机号!"))
  } else {
    callback()
  }
}

//校验身份证
function validID(rule, value, callback) {
  // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
  let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  if (value == null) {
    callback(new Error('请输入身份证号码'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('身份证号码不正确'))
  }
}

//校验地址
function checkAddress(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入地址,否则无法成功配送"))
  } else if (value.length > 400 || value.length < 4) {
    callback(new Error("地址长度应是4-400"))
  } else {
    callback()
  }
}

//校验蛋糕名称
function checkCakeName(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入蛋糕名称"))
  } else if (value.length > 12) {
    callback(new Error("蛋糕名称长度应是1-12"))
  } else {
    callback()
  }
}

// //校验蛋糕分类//不是input，无法校验
// function checkCakeType(rule, value, callback) {
//   if (value == null) {
//     callback(new Error("请选择蛋糕分类"))
//   } else {
//     callback()
//   }
// }

//校验蛋糕介绍
function checkCakeIntro(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入蛋糕介绍"))
  } else if (value.length > 400 || value.length < 8) {
    callback(new Error("蛋糕介绍长度应是8-400"))
  } else {
    callback()
  }
}

//校验蛋糕价格
function checkCakePrice(rule, value, callback) {
  if (value == null) {
    callback(new Error("请输入蛋糕价格"))
  } else if (value > 1000) {
    callback(new Error("蛋糕价格应当小于1000"))
  } else {
    callback()
  }
}