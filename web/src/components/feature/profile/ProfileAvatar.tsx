'use client';

import { ModalType } from '@/components/modal/Modal';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import Icon, { IconName } from '@/components/ui/icon';
import { useModalActions } from '@/store/modal';
import { useTranslations } from 'next-intl';

export default function ProfileAvatar() {
  const t = useTranslations();
  const { openModal } = useModalActions();

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Avatar>
          <AvatarImage src={''} />
          <AvatarFallback>
            <Icon name={IconName.USER} fill='none' />
          </AvatarFallback>
        </Avatar>
      </DropdownMenuTrigger>

      <DropdownMenuContent side='bottom' align='end'>
        <DropdownMenuItem icon={IconName.BELL}>
          {t('header.profile.notifications')}
        </DropdownMenuItem>

        <DropdownMenuItem
          icon={IconName.SETTINGS}
          onClick={() => {
            openModal(ModalType.SETTINGS);
          }}
        >
          {t('header.profile.settings')}
        </DropdownMenuItem>
        <DropdownMenuItem icon={IconName.SIGN_OUT}>
          {t('header.profile.sign-out')}
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
