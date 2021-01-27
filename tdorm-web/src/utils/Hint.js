export function errorHint(msg) {
    this.$message.error(msg);
}
export function successHint(msg) {
  this.$message({
    message: msg,
    type: 'success'
  });
}
export function normalHint(msg) {
  this.$message(msg);
}
export function successNotification(title,msg) {
  this.$notify({
    title: title,
    message: msg,
    duration: 0,
    type: 'success',
    position: 'bottom-right'
  });
}
