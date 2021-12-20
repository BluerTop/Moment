const TokenValue = 'TokenValue';
const MomentUser = 'MomentUser';
const MomentAuth = 'MomentAuth';

export function getMomentAuth() {
  return localStorage.getItem(MomentAuth);
}

export function setMomentAuth(momentAuth) {
  return localStorage.setItem(MomentAuth, momentAuth);
}

export function delMomentAuth() {
  return localStorage.getItem(MomentAuth) == null ? null : JSON.parse(localStorage.getItem(MomentAuth));
}

export function getTokenValue() {
  return localStorage.getItem(TokenValue);
}

export function setTokenValue(tokenValue) {
  return localStorage.setItem(TokenValue, tokenValue);
}

export function delTokenValue() {
  return localStorage.removeItem(TokenValue);
}

export function getMomentUser() {
  return localStorage.getItem(MomentUser) == null ? null : JSON.parse(localStorage.getItem(MomentUser));
}

export function setMomentUser(value) {
  return localStorage.setItem(MomentUser, value);
}

export function delMomentUser() {
  return localStorage.removeItem(MomentUser);
}
