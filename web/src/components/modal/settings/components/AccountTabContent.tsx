import { SettingsEntry } from '.';
import { useSettings } from './SettingsContext';
import { useGetMyProfileQuery } from '@/app/api/profiles/query';
import { Avatar } from '@/components/ui/avatar';
import { Input } from '@/components/ui/input';
import { useState } from 'react';

export default function AccountTabContent() {
  const { settings, update } = useSettings();
  const { data: profile } = useGetMyProfileQuery();

  const [name, setName] = useState(settings?.name || profile?.name || '');

  return (
    <>
      <SettingsEntry title='Profile Picture'>
        <Avatar></Avatar>
      </SettingsEntry>

      <SettingsEntry title='Name'>
        <Input
          type='text'
          value={name}
          onChange={(e) => {
            setName(e.target.value);
            update({
              name,
            });
          }}
        />
      </SettingsEntry>
    </>
  );
}
