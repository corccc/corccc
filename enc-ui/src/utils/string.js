export function formatInputValue(str) {
  const fmStr = str.replace(/0x/g, '');
  return fmStr.replace(/[^0-9a-fA-F]/g,'').replace(/\s/g, '');
}

export function formatLengthValue(str) {
  const fmStr = formatInputValue(str);
  const lenStr = Math.floor(fmStr.length / 2) + '(' + '0x' + Math.floor(fmStr.length / 2).toString(16) + ')'
  return lenStr;
}
