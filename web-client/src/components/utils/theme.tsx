'use client';

import { Switch } from '@/components/ui/switch';
import { Moon, Sun } from 'lucide-react';
import { useTheme } from 'next-themes';

export const ToggleDarkModeButton = () => {
  const { resolvedTheme, setTheme } = useTheme();

  return (
    <div className='flex justify-center items-center'>
      {resolvedTheme === 'dark' ? (
        <Moon className='h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all fill-black dark:rotate-0 dark:scale-100' />
      ) : (
        <Sun className='h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0' />
      )}

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
