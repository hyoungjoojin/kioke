'use client';

import { SettingsTab, settingsSidebarItems, settingsTabs } from './components';
import { Button } from '@/components/ui/button';
import { useModalStore } from '@/hooks/store/modal';
import { cn } from '@/lib/utils';
import { useState } from 'react';

export default function SettingsModal() {
  const { closeModal } = useModalStore();

  const [tab, setTab] = useState<SettingsTab>(SettingsTab.ACCOUNT);

  return (
    <div className='flex flex-col w-full h-full bg-gray-200 py-3 px-5'>
      <div className='flex justify-between items-center mt-2 mb-3'>
        <span className='font-bold text-lg'>Settings</span>
        <Button
          variant='ghost'
          onClick={() => {
            closeModal();
          }}
        >
          X
        </Button>
      </div>

      <div className='flex w-full h-full'>
        <div className='w-1/4 md:w-56 h-full pr-2'>
          {Object.values(SettingsTab).map((key) => {
            const settingsMenuItem = settingsSidebarItems[key];

            return (
              <Button
                variant='ghost'
                className={cn(
                  'flex justify-start items-center mb-1',
                  'w-full',
                  tab === key && 'bg-gray-300',
                )}
                key={key}
                onClick={() => {
                  setTab(key);
                }}
              >
                <span className='mr-1'>{settingsMenuItem.icon}</span>
                <span className='text-md'>{settingsMenuItem.name}</span>
              </Button>
            );
          })}
        </div>
        <div className='w-full h-full'>
          <div
            className={cn(
              'bg-white w-full h-full',
              'rounded-lg border shadow',
              'p-3',
            )}
          >
            {settingsTabs[tab]}
          </div>
        </div>
      </div>
    </div>
  );
}
