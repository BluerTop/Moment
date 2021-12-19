const TokenValue = 'TokenValue';

const MomentUser = 'MomentUser';

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
