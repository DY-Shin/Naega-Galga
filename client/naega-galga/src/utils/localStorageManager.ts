const accessTokenName = "accessToken";
const refreshTokenName = "refreshToken";

const localStorageManager = {
  getAccessToken: (): string | null => localStorage.getItem(accessTokenName),
  setAccessToken: (token: string): void => {
    localStorage.setItem(accessTokenName, token);
  },

  getRefreshToken: (): string | null => localStorage.getItem(refreshTokenName),
  setRefreshToken: (token: string): void =>
    localStorage.setItem(refreshTokenName, token),
  //토큰 업데이트
  tokenRefresh: function (accessToken: string, refreshToken: string): void {
    this.setAccessToken(accessToken);
    this.setRefreshToken(refreshToken);
  },
  removeTokens: () => {
    localStorage.removeItem(accessTokenName);
    localStorage.removeItem(refreshTokenName);
  },
  removeLocalStorage: () => {
    localStorage.clear();
  },
};

export default localStorageManager;
