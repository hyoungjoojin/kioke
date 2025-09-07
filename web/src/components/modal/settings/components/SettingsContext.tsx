import type { UpdateProfileRequest } from '@/app/api/profile';
import { useUpdateMyProfileMutationQuery } from '@/query/profile';
import { createContext, useContext, useState } from 'react';

type SettingsStatus = 'UPDATED' | 'PENDING' | 'IDLE';

type SettingsContextType = {
  isUpdated: boolean;
  isPending: boolean;
  updatedSettings: UpdateProfileRequest | null;
  updateSettings: (
    callback: (settings: UpdateProfileRequest) => UpdateProfileRequest,
  ) => void;
  cancel: () => void;
  submit: () => void;
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
  const [status, setStatus] = useState<SettingsStatus>('IDLE');
  const [updatedSettings, setUpdatedSettings] =
    useState<UpdateProfileRequest | null>({});

  const { mutate: updateProfile, isPending } =
    useUpdateMyProfileMutationQuery();

  const updateSettings = (
    callback: (settings: UpdateProfileRequest) => UpdateProfileRequest,
  ) => {
    setStatus('UPDATED');
    setUpdatedSettings((settings) => {
      return settings ? callback(settings) : settings;
    });
  };

  const submit = () => {
    if (updatedSettings) {
      updateProfile(updatedSettings);
    }

    setStatus('IDLE');
  };

  const cancel = () => {
    setUpdatedSettings(null);
    setStatus('IDLE');
  };

  return (
    <SettingsContext.Provider
      value={{
        isUpdated: status === 'UPDATED',
        isPending,
        updateSettings,
        updatedSettings,
        submit,
        cancel,
      }}
    >
      {children}
    </SettingsContext.Provider>
  );
}
