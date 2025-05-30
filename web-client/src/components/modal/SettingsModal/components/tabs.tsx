import AccountTab from './AccountTab';
import AppearanceTab from './AppearanceTab';
import Icon, { IconName } from '@/components/utils/icon';

enum SettingsTab {
  ACCOUNT = 'account',
  APPEARANCE = 'appearance',
}

const tabs: {
  [key in SettingsTab]: {
    sidebarIcon: React.ReactNode;
    title: string;
    tab: React.ReactNode;
  };
} = {
  [SettingsTab.ACCOUNT]: {
    sidebarIcon: null,
    title: 'Account',
    tab: <AccountTab />,
  },
  [SettingsTab.APPEARANCE]: {
    sidebarIcon: <Icon name={IconName.THEME} />,
    title: 'Appearance',
    tab: <AppearanceTab />,
  },
};

export default SettingsTab;
export { tabs };
