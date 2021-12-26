const mine = 'mine';
const focus = 'focus';

export function getFocus() {
  return localStorage.getItem(focus) === null ? 0 : localStorage.getItem(focus);
}

export function setFocus(top) {
  return localStorage.setItem(focus, top);
}

export function delFocus() {
  return localStorage.removeItem(focus);
}

export function getMine() {
  return localStorage.getItem(mine) === null ? 0 : localStorage.getItem(mine);
}

export function setMine(top) {
  return localStorage.setItem(mine, top);
}

export function delMine() {
  return localStorage.removeItem(mine);
}
