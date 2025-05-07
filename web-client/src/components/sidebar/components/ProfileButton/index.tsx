import { LogoutButton, SettingsButton } from './ProfileButtonDropdownMenuItems';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { ChevronDown } from 'lucide-react';

interface ProfileButtonProps {
  firstName: string;
  lastName: string;
}

export default function ProfileButton({
  firstName,
  lastName,
}: ProfileButtonProps) {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant='ghost'>
          <div className='flex items-center'>
            <Avatar>
              <AvatarFallback>{`${firstName[0]}${lastName[0]}`}</AvatarFallback>
            </Avatar>

            <p className='text-sm'>{firstName}</p>

            <ChevronDown size={16} />
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
