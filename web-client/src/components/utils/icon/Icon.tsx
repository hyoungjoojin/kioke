type IconSize = 'xs' | 'sm' | 'md' | 'lg';

export enum IconName {
  HOME = 'home',
  CLOSE = 'close',
  UP = 'up',
  DOWN = 'down',
  LEFT = 'left',
  RIGHT = 'right',
  THEME = 'theme',
  LIGHT_MODE = 'light-mode',
  DARK_MODE = 'dark-mode',
  MENU = 'menu',
  BELL = 'bell',
  LANGUAGE = 'language',
  TRASH = 'trash',
  HEART = 'heart',
  ELLIPSIS = 'ellipsis',
}

interface IconProps extends React.SVGProps<SVGSVGElement> {
  name: IconName;
  size?: IconSize;
  className?: string;
}

export default function Icon({
  name,
  size = 'md',
  className = '',
  ...props
}: IconProps) {
  let length = 0;
  switch (size) {
    case 'xs':
      length = 8;
      break;

    case 'sm':
      length = 10;
      break;

    case 'md':
      length = 18;
      break;

    case 'lg':
      length = 24;
      break;
  }

  return (
    <svg
      stroke='#000000'
      width={length}
      height={length}
      strokeLinecap='round'
      strokeLinejoin='round'
      className={className}
      {...props}
    >
      <use href={`/assets/icons/sprite.svg#${name}`} />
    </svg>
  );
}
