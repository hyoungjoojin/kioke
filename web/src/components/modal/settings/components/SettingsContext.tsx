import { createContext, useContext, useState } from 'react';

type SettingsContextType = {
  settings: Record<string, any>;
  update: (settings: Record<string, any>) => void;
  cancel: () => void;
  submit: () => void;
  isDirty: boolean;
};

export const SettingsContext = createContext<SettingsContextType | undefined>(
  undefined,
);

export const useSettings = () => {
  const context = useContext(SettingsContext);
  if (!context) {
    throw new Error('useSettings must be used within SettingsContext');
  }

  return context;
};

export function SettingsProvider({ children }: { children: React.ReactNode }) {
  const [settings, setSettings] = useState({});
  const [isDirty, setIsDirty] = useState(false);

  const update = (updatedSettings: Record<string, any>) => {
    setSettings((prevSettings) => ({
      ...prevSettings,
      ...updatedSettings,
    }));
    setIsDirty(false);
  };

  const cancel = () => {
    setSettings({});
    setIsDirty(false);
  };

  const submit = () => {
    console.log(settings);
    setIsDirty(false);
  };

  return (
    <SettingsContext.Provider
      value={{
        settings,
        update,
        submit,
        cancel,
        isDirty,
      }}
    >
      {children}
    </SettingsContext.Provider>
  );
}
