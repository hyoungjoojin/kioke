enum Theme {
  SYSTEM = 'system',
  LIGHT = 'light',
  DARK = 'dark',
}

function getThemeDisplayName(theme: Theme) {
  switch (theme) {
    case Theme.SYSTEM:
      return 'System';
    case Theme.LIGHT:
      return 'Light';
    case Theme.DARK:
      return 'Dark';
  }
}

export default Theme;
export { getThemeDisplayName };
