'use client';

import { Button } from '@/components/ui/button';
import { ModalType } from '@/constants/modal';
import { useModal } from '@/hooks/store/modal';
import { DropdownMenuItem } from '@radix-ui/react-dropdown-menu';
import { LogOut, Settings } from 'lucide-react';
import { signOut } from 'next-auth/react';

function ProfileButtonDropdownMenuItem(props: {
  icon: React.ReactNode;
  content: string;
  onClick: () => void;
}) {
  return (
    <DropdownMenuItem>
      <Button
        variant='ghost'
        className='w-full flex justify-start items-center p-2'
        onClick={props.onClick}
      >
        {props.icon}
        {props.content}
      </Button>
    </DropdownMenuItem>
  );
}

export function SettingsButton() {
  const { openModal } = useModal();

  return (
    <ProfileButtonDropdownMenuItem
      icon={<Settings className='h-4 w-4' />}
      content='Settings'
      onClick={() => {
        openModal(ModalType.SETTINGS);
      }}
    />
  );
}

export function LogoutButton() {
  return (
    <ProfileButtonDropdownMenuItem
      icon={<LogOut className='h-4 w-4' />}
      content='Log out'
      onClick={() => {
        signOut({ redirectTo: '/', redirect: true });
      }}
    />
  );
}
