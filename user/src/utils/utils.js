const setLocalStorage = function(key,value){
    window.localStorage.setItem(key,JSON.stringify(value))
}
const getLocalStorage = function(key){
    return JSON.parse(window.localStorage.getItem(key) || '')
}
export default{
    setLocalStorage,
    getLocalStorage
}