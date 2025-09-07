import { SettingsEntry } from '.';
import { useSettings } from './SettingsContext';
import { Input } from '@/components/ui/input';
import { useMyProfileQuery } from '@/query/profile';
import { useState } from 'react';

export default function AccountTabContent() {
  const { updateSettings, updatedSettings } = useSettings();
  const { data: profile } = useMyProfileQuery();

  const [name, setName] = useState(
    updatedSettings?.name || profile?.name || '',
  );

  return (
    <>
      <SettingsEntry title='Name'>
        <Input
          type='text'
          value={name}
          onChange={(e) => {
            setName(e.target.value);
            updateSettings((settings) => ({
              ...settings,
              name,
            }));
          }}
        />
      </SettingsEntry>
    </>
  );
}
