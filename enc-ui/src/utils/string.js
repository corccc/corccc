export function formatInputValue(str) {
  const fmstr = str.replace('0x', '');
  return fmstr.replace(/[^0-9a-fA-F]/g,'').replace(/\s/g, '');
}
