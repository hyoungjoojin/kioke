'use client';

import { signOut } from '@/app/api/auth';
import { ModalType } from '@/components/modal/Modal';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import Icon, { IconName } from '@/components/ui/icon';
import { destroySession } from '@/lib/session';
import { useMyDashboardQuery } from '@/query/dashboard';
import { useDashboardActions } from '@/store/dashboard';
import { useModalActions } from '@/store/modal';
import { useTranslations } from 'next-intl';

export default function ProfileAvatar() {
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
        <EditDashboard />
        <Settings />
        <SignOut />
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

function EditDashboard() {
  const { setIsEditingDraft, setDraft } = useDashboardActions();
  const { data: dashboard } = useMyDashboardQuery();

  if (!dashboard) {
    return null;
  }

  return (
    <DropdownMenuItem
      icon={IconName.EDIT}
      onClick={() => {
        setIsEditingDraft(true);
        setDraft(dashboard);
      }}
    >
      Edit Dashboard
    </DropdownMenuItem>
  );
}

function Settings() {
  const t = useTranslations();
  const { openModal } = useModalActions();

  return (
    <DropdownMenuItem
      icon={IconName.SETTINGS}
      onClick={() => {
        openModal(ModalType.SETTINGS);
      }}
    >
      {t('header.profile.settings')}
    </DropdownMenuItem>
  );
}

function SignOut() {
  const t = useTranslations();

  const signOutButtonClickHandler = async () => {
    await signOut();
    destroySession();
  };

  return (
    <DropdownMenuItem
      icon={IconName.SIGN_OUT}
      onClick={signOutButtonClickHandler}
    >
      {t('header.profile.sign-out')}
    </DropdownMenuItem>
  );
}
