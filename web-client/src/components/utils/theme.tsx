'use client';

import { Switch } from '@/components/ui/switch';
import { useTheme } from 'next-themes';

export const ToggleDarkModeButton = () => {
  const { resolvedTheme, setTheme } = useTheme();

  return (
    <div className='flex justify-center items-center'>
      {resolvedTheme === 'dark' ? null : null}

      <Switch
        checked={resolvedTheme === 'dark'}
        onCheckedChange={(checked) => {
          setTheme(checked ? 'dark' : 'light');
        }}
        className='mx-2'
      />
    </div>
  );
};
