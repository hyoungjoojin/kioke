'use client';

import { Button } from '@/components/ui/button';
import { ModalType } from '@/constants/modal';
import { useModalStore } from '@/hooks/store/modal';
import { DropdownMenuItem } from '@radix-ui/react-dropdown-menu';
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
  const { openModal } = useModalStore();

  return (
    <ProfileButtonDropdownMenuItem
      icon={null}
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
      icon={null}
      content='Log out'
      onClick={() => {
        signOut({ redirectTo: '/', redirect: true });
      }}
    />
  );
}
