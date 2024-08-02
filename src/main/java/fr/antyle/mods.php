<?php
const EXTFILES_URL = 'https://github.com/AntyleYT/AntylerMC/tree/master/src/main/java/fr/antyle';
const EXTFILES_FOLDER = __DIR__ . '/extfiles';
$json = [
    'extfiles' => []
];
$extfiles = [];
$directory = array_diff(scandir(EXTFILES_FOLDER), ['.', '..']);
foreach ($directory as $file) {
    $extfiles[] = $file;
}
foreach ($extfiles as $extfile) {
    $json['extfiles'][] = [
        'path' => $extfile,
        'downloadURL' => EXTFILES_URL . '/' . $extfile,
        'sha1' => sha1_file(EXTFILES_FOLDER . '/' . $extfile),
        'size' => filesize(EXTFILES_FOLDER . '/' . $extfile)
    ];
}
header('Content-Type: application/json');
echo json_encode($json);
