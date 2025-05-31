'use client';

import SettingsTab, { tabs } from './components';
import { Button } from '@/components/ui/button';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Separator } from '@/components/ui/separator';
import Icon, { IconName } from '@/components/utils/icon';
import { useModalActions } from '@/hooks/store/modal';
import { cn } from '@/lib/utils';
import { useState } from 'react';

export default function SettingsModal() {
  const { closeModal } = useModalActions();

  const [tab, setTab] = useState<SettingsTab>(SettingsTab.ACCOUNT);

  return (
    <div className='flex w-full h-full'>
      <div className='w-1/4 md:w-56 h-full pt-4 pl-4 pr-2'>
        <span className='font-bold text-lg'>Settings</span>

        {Object.values(SettingsTab).map((key) => {
          const settingsMenuItem = tabs[key];

          return (
            <Button
              variant='ghost'
              className={cn(
                'flex justify-start items-center my-1',
                'w-full',
                tab === key && 'bg-gray-300',
              )}
              key={key}
              onClick={() => {
                setTab(key);
              }}
            >
              <span className='mr-1'>{settingsMenuItem.sidebarIcon}</span>
              <span className='text-md'>{settingsMenuItem.title}</span>
            </Button>
          );
        })}
      </div>

      <div
        className={cn(
          'w-full h-full px-4',
          'flex flex-col',
          'bg-background',
          'rounded-r-2xl',
        )}
      >
        <div className='flex justify-between items-center pt-4'>
          <span className='text-lg font-bold'>{tabs[tab].title}</span>
          <Button
            variant='ghost'
            onClick={() => {
              closeModal();
            }}
          >
            <Icon name={IconName.CLOSE} size='lg' />
          </Button>
        </div>

        <Separator className='mb-2' />

        <ScrollArea className='flex-grow'>{tabs[tab].tab}</ScrollArea>

        <Separator className='mb-4' />

        <div className='self-end pb-4'>
          <Button
            variant='default'
            onClick={() => {
              closeModal();
            }}
            className='mx-4'
          >
            Cancel
          </Button>
          <Button variant='destructive'>Update</Button>
        </div>
      </div>
    </div>
  );
}
