'use client';

import { LogoutButton, SettingsButton } from './ProfileButtonDropdownMenuItems';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { useSession } from 'next-auth/react';

export default function ProfileButton() {
  const session = useSession();

  if (!session.data?.user) {
    return null;
  }

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant='ghost'>
          <div className='flex items-center'>
            <Avatar>
              <AvatarFallback>AA</AvatarFallback>
            </Avatar>

            <p className='text-sm'>{session.data.user.name}</p>
          </div>
        </Button>
      </DropdownMenuTrigger>

      <DropdownMenuContent loop align='start' sideOffset={10}>
        <SettingsButton />
        <DropdownMenuSeparator />
        <LogoutButton />
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
